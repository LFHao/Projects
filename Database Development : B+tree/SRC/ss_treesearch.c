#include "def.h"

ss_treesearch(PageNo,key,PageCount,WordCount)
PAGENO PageNo;
char *key;
int *PageCount;
int *WordCount;
{
    PAGENO           ChildPage;
    PAGENO           NextLFPage;
    struct  KeyRecord *KeyListTraverser;  /* Pointer to list of keys */
    struct  PageHdr   *PagePtr;
    PAGENO	FindPageNumOfChild();
    PAGENO	ss_searchLeaf();
    struct  PageHdr   *FetchPage();

    
    if(PageNo >= -1){
        PagePtr = FetchPage(PageNo);
        (*PageCount)++;
    }else{
        return;
    }
    
    if (IsLeaf(PagePtr)) {
        NextLFPage = ss_searchLeaf(PagePtr, key, WordCount);
        ss_treesearch(NextLFPage, key, PageCount, WordCount);
    }
    /* The root page contains zero keys */
    else if (IsNonLeaf(PagePtr))  {
        /* If not a leaf, keep going to left page */
        ss_treesearch(PagePtr->KeyListPtr->PgNum,key, PageCount, WordCount);
    }
    /* -christos-: free the space of PagePtr - DONE! */
    FreePage(PagePtr);
}
