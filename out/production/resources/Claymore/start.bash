set GPU_FORCE_64BIT_PTR 0
set GPU_MAX_HEAP_SIZE 100
set GPU_USE_SYNC_OBJECTS 1
set GPU_MAX_ALLOC_PERCENT 100
set GPU_SINGLE_ALLOC_PERCENT 100

./ethdcrminer64 -epool eth-us-east1.nanopool.org:9999 -ewal 0x36f536f54ccec727f861d6622e465003a731fe41/isaiahminer2/lx93@me.com -epsw x -tt 75 -fanmin 60 -fanmax 90 -dcri 4 -cclock 1165 -mclock 2150 -cvddc 810 -mvddc 810
