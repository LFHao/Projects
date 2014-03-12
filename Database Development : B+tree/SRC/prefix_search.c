#include "def.h"
extern FILE *fpbtree;
extern int sqCount;	/* statistics: # of successf. queries asked */
extern int uqCount;	/* # of unsuccessf. queries */

prefix_search(key, flag)
char    *key;
int     flag;
{
    int PageCount = 0;
    int WordCount = 0;
    
    /* Print an error message if strlen(key) > MAXWORDSIZE */
    if (strlen(key) > MAXWORDSIZE) {
        printf ("ERROR in \"search\":  Length of key Exceeds Maximum Allowed\n");
        printf (" and key May Be Truncated\n");
    }
    if( iscommon(key) ) {
        printf("\"%s\" is a common word - no searching is done\n",key);
        return;
    }
    if( check_word(key) == FALSE ) {
        return;
    }
    /* turn to lower case, for uniformity */
    strtolow(key);
    
    prefix_treesearch(ROOT,key,&PageCount, &WordCount);
    if(WordCount == 0) {
        printf("key \"%s\": not found\n", key);
        printf("%d pages read\n", PageCount);
        uqCount++;
    } else {
        if(flag) {
            printf("\"%s\" is the prefix of %d words\n", key, WordCount);
            printf("%d pages read\n", PageCount);
            sqCount++;
        } else {
            printf("Found the key!\n");
        }
    }
}
