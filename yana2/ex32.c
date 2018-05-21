/*
 * Yana Patyuk 317106755
 */

#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include<sys/types.h>
#include <sys/wait.h>
#include<stdlib.h>
#include <string.h>
#include <stdio.h>
#include <dirent.h>


void ReadPaths(char* filePath, char* students, char* input, char* output);
int FindCFilesOFStudent(char* path);
int IsCType(char* fileName);
int GetNumberOfCharsInRow(char* row);

enum boolean{false, true};
const char* MSG[] = {",0,NO_C_FILE\n", ",0,COMPILATION_ERROR\n",",0,TIMEOUT\n",
											",60,BAD_OUTPUT\n", ",80,SIMILAR_OUTPUT\n", ",100,GREAT_JOB\n"};


int main (int argc, char* argv[]){

	DIR *masterDir;
	struct dirent *mDirent;
	struct stat stat_p;
	int score = 0;
	int resultFile, infoFile, input, output, charNum, tamp;
	char folderPath[160] = {0}, inputPath[160] = {0}, outPutPtah[160];
	char currentFilePath[160] = {0}, studentCFile[160] = {0};



	//if input doesn't contain 3 parameters.
	if(argc != 2) return 1;
	//open new file to write the result of the chack
	if((resultFile=open("results.cvs",O_WRONLY))==-1) {
		exit(-1);
	}
	//read rows from given file
	ReadPaths(argv[1],folderPath, inputPath,outPutPtah);


	//if directory cant open.
	if ((masterDir = opendir(folderPath)) == NULL)
			exit(1);
	// looping through the directory, printing the directory entry name
	while ((mDirent = readdir(masterDir)) != NULL){
		//if(S_ISDIR(mDirent->d_name)==0)
		if(stat(mDirent->d_name, &stat_p)== -1) /* declare the 'stat' structure */
			return 0;
		if(S_ISDIR(stat_p.st_mode)==0 &&
				(strcmp(mDirent->d_name,"..")!=0)&&
				(strcmp(mDirent->d_name,".")!=0)) {//if stat is directory-means is a student
			strcpy(currentFilePath,folderPath);
			strcat(currentFilePath, mDirent->d_name);
			if(FindCFilesOFStudent(currentFilePath, studentCFile) == false) {
				score = 0;//check students work.
			} else {

			}
			//write the answer in the file "name + MSG".
			//note:score refers to the massage.
			bzero(currentFilePath,160);
			bzero(studentCFile,160);
		  if(write(resultFile,mDirent->d_name, strlen(mDirent->d_name)) <=0) {
			  //error
		  }
		  if((write(resultFile,MSG[score], strlen(MSG[score])) <=0)) {
			  //error
		  }
		}
	}
	closedir(mDirent);
	close(resultFile);
	return 0;
}

int CompileAndRunAndChack(char* studentFile, char* input, char* output) {
    pid_t val, val2 ,val3;
    int value, resultFile;
    char* paramCompil[] = { "gcc", "-o", "program.out" , studentFile, NULL};
    char* programRun[] = {"./program.out", NULL };
    char* compereParm[] = { "comp.out", resultFile, output, NULL };
    execvp("gcc",paramCompil );
    val = fork();
    if(val == 0) {//child

    execvp("gcc",paramCompil);
    } else {//parent
    	wait(&val);
    	if(FileExict("program.out")) {
    		val = fork();
    		if(val == 0) {
    			if((resultFile=open("results.cvs",O_WRONLY))==-1) {
    					exit(-1);
    				}
    			dup2(input, 0);
    			dup2(resultFile, 1);
    			execvp("./program.out", programRun);
    		} else {
    			sleep(5);
    			if (waitpid(val, NULL, WNOHANG) != 0) {
    				return 3;
    			}
    			val = fork();
    			if(val == 0) {
    				execvp("./comp.out", compereParm);
    			} else {
    				wait(&val);
    				return val + 2;
    			}
    		}
    	} else {
    		return 1;
    	}
    }

	return 0;
}

int FileExict(const char * filename){
    /* try to open file to read */
    FILE *file;
    if ((file = fopen(filename, "r"))){
        fclose(file);
        return true;
    }
    return false;
}

/**
 * inpue:string to path of students folder.
 * find in path C file and check it's score.
 * if no such file exist return 0.
 * note:msg number refers to the order of messages in global MSG.
 */
int FindCFilesOFStudent(char* path, char* CPathFile) {
	DIR *masterDir;
	struct dirent *mDirent;
	struct stat stat_p;
	int msgNumber = false;
	char tampPath[160];

	if ((masterDir = opendir(path)) == NULL)
			return false;
	// looping through the directory, printing the directory entry name
	while ((mDirent = readdir(masterDir)) != NULL){
		if(stat(mDirent->d_name, &stat_p)== -1) /* declare the 'stat' structure */
			return false;
		if(S_ISDIR(stat_p.st_mode)==0) {//if stat is directory-find in the folder
			strcpy(tampPath, path);
			strcat(tampPath,mDirent->d_name);
			msgNumber = FindCFilesOFStudent(tampPath);
			bzero(tampPath,160);
			if(msgNumber != false) break;
		}else if(IsCType(mDirent->d_name) == true) {//compile the file
			msgNumber = true;
			strcpy(tampPath, path);
			strcat(tampPath,mDirent->d_name);
			strcpy(CPathFile, path);
			break;
		}
	}
	return msgNumber;
}

/**
 * check if given file name is c type (ends with ".c").
 * return false if not.
 */
int IsCType(char* fileName) {
	int lengthOfName = strlen(fileName);
	if((fileName[lengthOfName - 2] == '.') &&
			(fileName[lengthOfName - 1]== 'c')) {
		return true;
	}
		return false;
}

void CompileAndRun(char* fileName, char* path) {

}
/**
 * input: pointer to the start of the row
 * return number of chars till end of the row
 */
int GetNumberOfCharsInRow(char* row) {
	int sum = 0;
	while(row[sum] != '\n') {
		sum++;
	}
	return sum;
}
/**
 * read from file path information and copy it to given pointers to arrays.
 * note: we read each time 160 char's but we copy only till the end of current row.
 */
void ReadPaths(char* filePath, char* students, char* input, char* output) {
	int infoFile, charNum, tamp;//charNum: means how many bytes in the row.
	char buffer[160];
	//open new file to write the result of the chack
	if((infoFile=open(filePath,O_RDONLY))==-1) {
		exit(-1);
	}
	//read first row
	if((charNum =read(infoFile, buffer,160))<=0) {
		//exit
	}
	charNum = GetNumberOfCharsInRow(buffer);
	strncpy(students, buffer, charNum);
	//read second row. "jump" to the end of first row and read from there.
	if(lseek(infoFile, charNum, SEEK_SET) < 0) return;
	if((charNum =read(infoFile, buffer,160))<=0) {
			//exit
	}
	tamp = charNum;
	charNum = GetNumberOfCharsInRow(buffer);
	strncpy(input, buffer, charNum);
	//read third row. "jump" to the end of seconf row and read from there.
	if(lseek(infoFile, charNum + tamp, SEEK_SET) < 0) return;
	if((charNum =read(infoFile, buffer,160))<=0) {
				//exit
	}
	charNum = GetNumberOfCharsInRow(buffer);
	strncpy(output, buffer, charNum);
}

int CheckSimmilarity(char* studentOutput, char* comparedOutput) {
	char *params[] = {"comp.out", studentOutput, comparedOutput, NULL};

	execvp("comp.out",params);
}

