#include "def.h"

prefix_treesearch(PageNo, key, PageCount, WordCount)
PAGENO PageNo;
char *key;
int *PageCount;
int *WordCount;
{
    PAGENO           ChildPage;
    PAGENO           NextLFPage;
    struct KeyRecord *KeyListTraverser;  /* Pointer to list of keys */
    struct PageHdr   *PagePtr;
    PAGENO	FindPageNumOfChild();
    PAGENO	prefix_searchLeaf();
    struct PageHdr   *FetchPage();
    
    if(PageNo >= -1){
        PagePtr = FetchPage(PageNo);
        (*PageCount)++;
    }else{
        return;
    }
    
    if (IsLeaf(PagePtr)) {
        NextLFPage = prefix_searchLeaf(PagePtr, key, WordCount);
        prefix_treesearch(NextLFPage, key, PageCount, WordCount);
    }
    /* The root page contains zero keys */
    else if ((IsNonLeaf(PagePtr)) && (PagePtr->NumKeys == 0))  {
        /* keys, if any, will be stored in Page# 2
         THESE PIECE OF CODE SHOULD GO soon! **/
        prefix_treesearch(FIRSTLEAFPG,key,PageCount,WordCount);
    } else if ((IsNonLeaf(PagePtr)) && (PagePtr->NumKeys > 0))  {
        KeyListTraverser = PagePtr->KeyListPtr;
        ChildPage = FindPageNumOfChild(PagePtr,KeyListTraverser,
                                       key,PagePtr->NumKeys);
        prefix_treesearch(ChildPage,key,PageCount,WordCount);
    }
    /* -christos-: free the space of PagePtr - DONE! */
    FreePage(PagePtr);
}
