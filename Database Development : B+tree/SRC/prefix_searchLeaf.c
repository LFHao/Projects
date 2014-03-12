#include "def.h"

PAGENO prefix_searchLeaf(PagePtr, key, WordCount)
struct PageHdr *PagePtr;
char *key;
int  *WordCount;
{
    struct  KeyRecord *KeyListTraverser;
    /* Position for insertion */
    int     *PrefixPosition;
    int     Count, Found, i;
    struct PageHdr   *FetchPage();
    int * FindPrefixPosition();
    
    Count = 0;
    
    /* Find perfix position */
    KeyListTraverser = PagePtr->KeyListPtr;
    PrefixPosition = FindPrefixPosition(KeyListTraverser,key,&Found,
                                              PagePtr->NumKeys,Count);
    
    /* key is already in the B-Tree */
    if (Found == TRUE) {
        for (i = 0; i < 1000; i++){
            if(KeyListTraverser){
                if(*(PrefixPosition+i) == 1){
                    printf("Prefix found in %s\n", KeyListTraverser->StoredKey);
                    (*WordCount)++;
                }
                KeyListTraverser = KeyListTraverser->Next;
            }else{
                break;
            }
        }
        if(*(PrefixPosition + (PagePtr->NumKeys)-1) == 1)
            return PagePtr->PgNumOfNxtLfPg;
        else
            return NONEXISTENT;
    } else {
        /*If no prefix found, return pageno = -1*/
        return(NONEXISTENT);
    }
}
