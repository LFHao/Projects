#include "def.h"

CheckPrefix(Key, Word)
char *Key,    /* Possible New Key */
*Word;   /* The Key Stored in the B-Tree */
{
    int m = max(strlen(Key), strlen(Word));
    
	int i = 0;
	for(i = 0; i < m; i++) {
        if (Key[i] < Word[i]) {
            return(1);
        } else if (Key[i] > Word[i]) {
            return(2);
        } else if ( i == strlen(Key) - 1){
			return(0);
		} else if ( i == strlen(Word)-1) {
			return(2);
        }
	}
	return(1);
}


