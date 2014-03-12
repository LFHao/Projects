#include "def.h"

int * FindPrefixPosition(KeyListTraverser,Key,Found,NumKeys,Count)
struct KeyRecord *KeyListTraverser;  /* Pointer to the list of keys */
int              *Found,
Count;
NUMKEYS           NumKeys;
char             *Key;       /* The new possible key */
{
    int    Result;
    int    CheckPrefix();
    static int  res[1000] = {0};
    
    /* -christos- the next block probably provides for
     insertion in empty list (useful for insertion in root
     for the first time! */
    
    if( NumKeys == 0 ) {
        *Found = FALSE;
        return &res[0];
    }
    
    while(NumKeys>0){
        Result = CheckPrefix(Key, KeyListTraverser->StoredKey);
        if (Result == 0){
            *Found = TRUE;
            res[Count] = 1;
        }else{
            res[Count] = 0;
        }
        NumKeys = NumKeys - 1;
        Count = Count + 1;
        KeyListTraverser = KeyListTraverser->Next;
    }
    
    return &res[0];
}



