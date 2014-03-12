/*********************************************************************
*                                                                    *
*    This function searches the b-tree for the given word
		key
     and prints the associated postings list.
*                                                                    *
*********************************************************************/


#include "def.h"
extern FILE *fpbtree;
extern int sqCount;	/* statistics: # of successf. queries asked */
extern int uqCount;	/* # of unsuccessf. queries */

search(key, flag)
char    *key;
int     flag;
{

    POSTINGSPTR treesearch();
    POSTINGSPTR pptr;
    int PageCount = 0;

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

    pptr = treesearch(ROOT,key, &PageCount);
    if( pptr == NONEXISTENT) {
        printf("key \"%s\": not found\n", key);
        uqCount++;
    } else {
        if(flag) {
            getpostings(pptr);
            printf("%d pages read\n", PageCount);
            sqCount++;
        } else {
            printf("Found the key!\n");
        }
    }

}
