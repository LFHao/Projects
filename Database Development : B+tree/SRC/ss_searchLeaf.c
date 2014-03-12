#include "def.h"

PAGENO ss_searchLeaf(PagePtr, key, WordCount)
struct PageHdr *PagePtr;
char *key;
int  *WordCount;
{
    struct  KeyRecord *KeyListTraverser;
    /* Position for insertion */
    int     *SSPosition;
    int     Count, Found, i;
    struct PageHdr   *FetchPage();
    int *FindSSPosition();
    
    Count = 0;
    
    /* Find perfix position */
    KeyListTraverser = PagePtr->KeyListPtr;
    SSPosition = FindSSPosition(KeyListTraverser,key,&Found,
                                        PagePtr->NumKeys,Count);
    
    /* key is already in the B-Tree */
    if (Found == TRUE) {
        for (i = 0; i < 1000; i++){
            if(KeyListTraverser){
                if(*(SSPosition+i) == 1){
                    printf("Substring found in %s\n", KeyListTraverser->StoredKey);
                    (*WordCount)++;
                }
                KeyListTraverser = KeyListTraverser->Next;
            }else{
                break;
            }
        }
        return PagePtr->PgNumOfNxtLfPg;
    } else {
        return PagePtr->PgNumOfNxtLfPg;
    }
}
