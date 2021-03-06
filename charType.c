#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <assert.h>

#define maxStrLength 100 //max string length

void extract_chars(char* s, char* a, char* d, char* p, char* w);

int main(int argc, char*, argv[]) {
  FILE* in;
  FILE* out;
  char* line;
  char* word;
  char* number;
  char* punctuation;
  char* whitespace;
  int lineNum = 1;
  
  if (argc != 3) {
    printf("Usage: %s <input file> <output file>\n", argv[0]);
    exit(EXIT_SUCCESS);
  in = fopen(argv[1], "r");
  if (in == NULL) { //no input
    printf("Unable to read from file %s\n", argv[1]);
    exit(EXIT_FAILURE);
  }
  
  out = fopen(argv[2], "w");
  if (out == NULL) { //no output
    printf("Unable to write to file %s\n", argv[2]);
    exit(EXIT_FAILURE);
  }
  
  line = calloc(maxStrLength + 1, sizeof(char));
  word = calloc(maxStrLength + 1, sizeof(char));
  number = calloc(maxStrLength + 1, sizeof(char));
  punctuation = calloc(maxStrLength + 1, sizeof(char));
  whitespace = calloc(maxStrLength + 1, sizeof(char));
  assert( line != NULL && word != NULL && number != NULL && punctuation != NULL && whitespace != NULL);
  
  // read input file, correct type
  while (fgets(line, maxStrLength, in) != NULL) {
    extract_chars(line, word, number, punctuation, whitespace);
    fprintf(out, "line %d contains: \n", lineNum);
    //word
    if (strlen(word)>1) {
      fprintf(out, "%d alphabetic characters: %s\n", (intstrlen(word), word));
    } else {
      fprintf(out, "%d alphabetic characters: %s\n", (intstrlen(word), word));
    }
    //number
    if (strlen(word)>1) {
      fprintf(out, "%d alphabetic characters: %s\n", (intstrlen(number), number));
    } else {
      fprintf(out, "%d alphabetic characters: %s\n", (intstrlen(number), number));
    }
    //punctuation
    if (strlen(word)>1) {
      fprintf(out, "%d alphabetic characters: %s\n", (intstrlen(punctuation), punctuation));
    } else {
      fprintf(out, "%d alphabetic characters: %s\n", (intstrlen(punctuation), punctuation));
    }
    //whitespace
    if (strlen(whitespace)>1) {
      fprintf(out, "%d alphabetic characters: %s\n", (intstrlen(whitespace), whitespace));
    } else {
      fprintf(out, "%d alphabetic characters: %s\n", (intstrlen(whitespace), whitespace));
    }
    lineNum++;
  }
  
  //free allocated heap memory
  free(line);
  free(word);
  free(number);
  free(punctuation);
  free(whitespace);
  
  //close files
  fclose(in);
  fclose(out);
  
  return(EXIT_SUCCESS);
}

void extract_chars(char* s, char* a, char* d, char* p, char* w) {
  int i=0, j=0, k=0, l=0, m=0;
  while(s[i] != '\0' && i<maxStrLength) {
    if (isupper((int)s[i])) {
      a[j]=s[i];
      j++;
    } else if (isalpha((int)s[i])) {
      a[j]=s[i];
      j++;
    } else if (isdigit((int)s[i])) {
      d[k] = s[i];
      k++;
    } else if (ispunct((int)s[i])) {
      p[l] = s[i];
      l++;
    } else {
      w[m] = s[i];
      m++;
    }
    i++;
  }
  a[j] = '\0';
  d[k] = '\0';
  p[l] = '\0';
  w[m] = '\0';
}