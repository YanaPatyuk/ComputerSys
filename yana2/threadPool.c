/*
*Yana Patyuk
*317106755
 */
#include "threadPool.h"
pthread_mutex_t m;
pthread_mutex_t mDestroy;
pthread_cond_t cond;


int run;
int tasks = 0;

ThreadPool* tpCreate(int numOfThreads) {
int err;
if (pthread_mutex_init(&m, NULL) != 0)
  {
      printf("\n mutex init failed\n");
      return NULL;
  }
if (pthread_mutex_init(&mDestroy, NULL) != 0)
  {
      printf("\n mutex init failed\n");
      return NULL;
  }
cond=PTHREAD_COND_INITIALIZER;

	ThreadPool *pool = malloc(sizeof(ThreadPool));
	if(pool == NULL)
		return NULL;
	run = 1;
	pool->numberOfThreads = numOfThreads;
	pool->taskQueue = osCreateQueue();
	pool->taskQueueArgs = osCreateQueue();
	pool->threads =  malloc(numOfThreads * sizeof(pthread_t));
	for(int i = 0; i < numOfThreads; i++){
		err =  pthread_create(pool->threads[i] ,NULL, executeTasks,(void*)pool);
		if(err != 0)
			 printf("\ncan't create thread :[%s]", strerror(err));
	}
	pool->destroy = FALSE;
	return pool;
}


void tpDestroy(ThreadPool* threadPool, int shouldWaitForTasks) {
	if(!threadPool->destroy) {
		pthread_mutex_lock(&mDestroy);
		if(!threadPool->destroy) {
			threadPool->destroy = TRUE;
			pthread_mutex_unlock(&mDestroy);
			//pthread_cond_wait()
			if(shouldWaitForTasks == 0) {
				pthread_mutex_lock(&m);
				osDestroyQueue(threadPool->taskQueue);
				osDestroyQueue(threadPool->taskQueueArgs);
				pthread_mutex_unlock(&m);
			}
		  pthread_cond_wait(&cond, &tasks);
			run = 0;
			for (int i=0;i<threadPool->numberOfThreads;i++)
			    	pthread_join(threadPool->threads[i], NULL);
			free(threadPool->threads);
			free(threadPool->taskQueue);
			free(threadPool->taskQueueArgs);
		}
	}
}

int tpInsertTask(ThreadPool* threadPool, void (*computeFunc) (void *), void* param) {
	if(!threadPool->destroy) {
		pthread_mutex_lock(&m);
		osEnqueue(threadPool->taskQueue, computeFunc);
		osEnqueue(threadPool->taskQueueArgs, param);
		tasks = 1;
		pthread_mutex_unlock(&m);
		return 0;
	}
	return -1;
}


void* executeTasks(void* arg) {
	ThreadPool *pool = (ThreadPool)arg;
	void* task;
	void* args;
	while(run) {
		pthread_mutex_lock(&m);
		if(!osIsQueueEmpty(pool->taskQueue)) {
			task = osDequeue(pool->taskQueue);
			args = osDequeue(pool->taskQueueArgs);
			if(osIsQueueEmpty(pool->taskQueue))
				tasks = 0;
			pthread_mutex_unlock(&m);
			(*task)(*args);
			free(task);
			free(args);
		} else {
      pthread_cond_wait(&cond, &tasks);
			pthread_mutex_unlock(&m);
		}
	}
	return NULL;
}

