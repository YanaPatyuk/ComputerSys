/*
*Yana Payuk
*317106755
 */

#include<sys/types.h>
#include<unistd.h>
#include<stdlib.h>
#include <string.h>
#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>

    struct node
    {
        int val;
        	int head;
        char* name;
        struct node *nextJob;
    };
    typedef struct node JOB;
    void addToList(JOB** list, pid_t pid, char* name, int size);
    void refreshList(JOB** list);
    void deleteAll(JOB** list);
    void printList(JOB** list);


int main(char* args) {
	char argumant[250];
	char copy[250];
	char* token;
	char **arrayArgs=NULL;
	int exit = 1, i;
	JOB* list = NULL;
	pid_t value;
	char prompt[8] = "prompt>";

	while(exit) {
		i=0;
		//***get from user the command, copy it for backup and split to words***
		printf("%s", prompt);
		fgets(argumant, 250, stdin);
		//delete space
		argumant[strlen(argumant) - 1] = 0;
		strcpy(copy, argumant);
		token =  strtok(argumant, " ");
		/* split string and append tokens to 'arrayArgs' */
		while (token) {
		  arrayArgs = realloc (arrayArgs, sizeof (char*) * ++i);
		  if (arrayArgs == NULL) {
		    /* memory allocation failed */
		    deleteAll(&list);
		    return (-1);
		  }
		  arrayArgs[i-1] = token;
			token = strtok (NULL, " ");
		}
			/* realloc one extra element for the last NULL */
			arrayArgs = realloc (arrayArgs, sizeof (char*) * (i+1));
			arrayArgs[i] = 0;


			//check users command//

		if(strcmp("jobs", *arrayArgs) == 0) {
			//refresh jobs in list
			refreshList(&list);
			//print the jobs
			printList(&list);

		}else if(strcmp("exit", *arrayArgs) == 0) {
			exit = 0;
			printf("%d\n",getpid());
			deleteAll(&list);

		}	else if(strcmp(arrayArgs[0],"cd")== 0) {
			printf("%d\n",getpid());
			if(arrayArgs[1] == 0)//no path-go  back to home
				chdir(getenv("HOME"));
			else//go to path
			  chdir(arrayArgs[1]);

  	} else if(strcmp(arrayArgs[0], "cd~") == 0) {
		printf("%d\n",getpid());

		} else if(strcmp(arrayArgs[0], "cd-") == 0) {
			printf("%d\n",getpid());

		} else if(strcmp(arrayArgs[0], "cd..") == 0) {

		}else {//any other command
			value = fork();
			if(value < 0) {
							//error
		  } else if(value == 0) {//child
				if(strcmp(arrayArgs[i - 1], "&") == 0) {
					arrayArgs[i - 1] = 0;
					}
			  execvp(*arrayArgs, arrayArgs);
			  return 0;//not suppose to return to here.

		  } else {
				//print id number
				printf("%d\n",value);
				if(strcmp(arrayArgs[i - 1], "&") == 0) {
					//delete the sigh.
					copy[strlen(copy) - 1] = 0;
					copy[strlen(copy) - 1] = 0;
					addToList(&list, value, copy, strlen(copy));
				} else {
					addToList(&list, value, copy, strlen(copy));
					wait(value);
				}
		   }
		}
		//clear the list
		for(int j=0; j < i; j++){
			arrayArgs[i] = 0;
		}
	}
//free the memory
	free(arrayArgs);

	return 0;
}

void addToList(JOB** list, pid_t pid, char* name, int size) {
	JOB* new_node = (JOB*)malloc(sizeof(JOB));
	new_node->name  = malloc(size);
	if(new_node->name == NULL) {
		return;
	}
	//copy info
	 int i;
	 for (i=0; i<size; i++)
	        *(char *)(new_node->name + i) = *(char *)(name + i);
	 if(*list != NULL) {
		 new_node->nextJob = *list;
		 new_node->head = 1;
		 new_node->nextJob->head = 0;
		 *list = new_node;
	 } else {
		 new_node->nextJob = NULL;
		 new_node->head = 1;
		 *list = new_node;
	 }
	 new_node->val = (int) pid;
}

void refreshList(JOB** list) {
	JOB* temp = *list, *temp2, *prev;

	  while (temp != NULL) {
		 // check if the job ended? and delete from list.
		  if((waitpid(temp->val, NULL, WNOHANG)) !=0) {
			  //update the head
			  if(temp->head == 1) {
				  *list = temp->nextJob;
				  if(*list != NULL){
					  temp->nextJob->head = 1;
				  }
			  }
			  temp2 = temp;
			  temp = temp->nextJob;
			  prev->nextJob = temp2->nextJob;
			  free(temp2->name);
			  free(temp2);
		  }else {
			prev = temp;
		  temp = temp->nextJob;
		  }
	    }
}

void deleteAll(JOB** list) {
	JOB* temp = *list;
	JOB* temp2;
	  while (temp != NULL)
	  {
		  temp2 = temp;
		  temp = temp->nextJob;
		  free(temp->name);
		  free(temp2);
	    }
}

void printList(JOB** list) {
	JOB* temp;
	if(list != NULL) {
		temp = *list;
		  while (temp != NULL)
		  {
			  printf("%d \t %s\n", temp->val, temp->name);
			  temp = temp->nextJob;
		    }
	}
}

