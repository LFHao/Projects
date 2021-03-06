/* calculates the number of bytes and number of keys
   for a given
	PagePtr		page image
   and fills in the appropriate fields in the structure
 */

#include "def.h"

fillIn(PagePtr)
struct PageHdr *PagePtr;      /* Pointer to page being filled-in */
{
    struct KeyRecord *p;
    NUMBYTES bytes;
    NUMKEYS  keys;

    bytes =  sizeof(PagePtr->PgTypeID) +
             sizeof(PagePtr->PgNum) +
             sizeof(PagePtr->PgNumOfNxtLfPg) +
             sizeof(PagePtr->NumBytes) +
             sizeof(PagePtr->NumKeys);

    keys = 0;

    for( p= PagePtr->KeyListPtr ; p!=NULL ; p=p->Next) {
        keys ++;
        bytes += p->KeyLen +
                 sizeof(POSTINGSPTR) + sizeof(KEYLEN);
    }

    PagePtr->NumBytes = bytes;
    PagePtr->NumKeys = keys;
    return;
}

