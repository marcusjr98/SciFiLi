
   SSSSSSSSSSSSSSS         CCCCCCCCCCCCC     IIIIIIIIII     FFFFFFFFFFFFFFFFFFFFFF     IIIIIIIIII     LLLLLLLLLLL                  IIIIIIIIII
 SS:::::::::::::::S     CCC::::::::::::C     I::::::::I     F::::::::::::::::::::F     I::::::::I     L:::::::::L                  I::::::::I
S:::::SSSSSS::::::S   CC:::::::::::::::C     I::::::::I     F::::::::::::::::::::F     I::::::::I     L:::::::::L                  I::::::::I
S:::::S     SSSSSSS  C:::::CCCCCCCC::::C     II::::::II     FF::::::FFFFFFFFF::::F     II::::::II     LL:::::::LL                  II::::::II
S:::::S             C:::::C       CCCCCC       I::::I         F:::::F       FFFFFF       I::::I         L:::::L                      I::::I
S:::::S            C:::::C                     I::::I         F:::::F                    I::::I         L:::::L                      I::::I
 S::::SSSS         C:::::C                     I::::I         F::::::FFFFFFFFFF          I::::I         L:::::L                      I::::I
  SS::::::SSSSS    C:::::C                     I::::I         F:::::::::::::::F          I::::I         L:::::L                      I::::I
    SSS::::::::SS  C:::::C                     I::::I         F:::::::::::::::F          I::::I         L:::::L                      I::::I
       SSSSSS::::S C:::::C                     I::::I         F::::::FFFFFFFFFF          I::::I         L:::::L                      I::::I
            S:::::SC:::::C                     I::::I         F:::::F                    I::::I         L:::::L                      I::::I
            S:::::S C:::::C       CCCCCC       I::::I         F:::::F                    I::::I         L:::::L         LLLLLL       I::::I
SSSSSSS     S:::::S  C:::::CCCCCCCC::::C     II::::::II     FF:::::::FF                II::::::II     LL:::::::LLLLLLLLL:::::L     II::::::II
S::::::SSSSSS:::::S   CC:::::::::::::::C     I::::::::I     F::::::::FF                I::::::::I     L::::::::::::::::::::::L     I::::::::I
S:::::::::::::::SS      CCC::::::::::::C     I::::::::I     F::::::::FF                I::::::::I     L::::::::::::::::::::::L     I::::::::I
 SSSSSSSSSSSSSSS           CCCCCCCCCCCCC     IIIIIIIIII     FFFFFFFFFFF                IIIIIIIIII     LLLLLLLLLLLLLLLLLLLLLLLL     IIIIIIIIII


  _             __  __
 | |           |  \/  |                            ___
 | |__  _   _  | \  / | __ _ _ __ ___ _   _ ___   ( _ )
 | '_ \| | | | | |\/| |/ _` | '__/ __| | | / __|  / _ \/\
 | |_) | |_| | | |  | | (_| | | | (__| |_| \__ \ | (_>  <
 |_.__/ \__, | |_|  |_|\__,_|_|  \___|\__,_|___/  \___/\/
   _____ __/ |
  |  __ \___/
  | |  | | _____   _____  _ __
  | |  | |/ _ \ \ / / _ \| '_ \
  | |__| |  __/\ V / (_) | | | |
  |_____/ \___| \_/ \___/|_| |_|


Hello and thank you for using this program.
This program was created by Marcus Castille Jr. and Devon Knudsen

For this project we chose to use an arrayList, priority queue and a stack.
We chose to use an arrayList because you can easily access any index by using
a binary search instead of having to iterate over the entire set of data every
time. ArrayList also have a dynamic size so you can add to them without hassle.
We chose to use the priority queue because, in case of a fire, the librarian needs
an ordered list of book sorted by importance. This data structure seems like a no
brainer due to its functionality being just that, ordering the objects based on its
priority. We chose to use a stack for checking in and checking out in order to emulate
the actual physical process of placing a pile of books in front of the librarian and
having each book scanned individually. Popping a book off of the stack is similar to
having the librarian scan it.

In this program there are two types of users: admins, and non admins

An admin can check in any book, but cannot checkout a book that is already checked out. An admin can
also close the library a closing file is generated when this happens. Admin can also create new users.

A non admin can only check in books that they have checked out. Similar to admins they cannot check out
books that are already checked out.

You have to be logged in to use this program.
Please type 'help' to receive directions at any time


To test the full functionality of this program we recommend creating at least 2 new users, we will call
them userA and userB. check out books for userA then log out and log in as userB and do the same. Then
you may view the checked out books for userB and note the books checked out by userA do not show up. You
can also try to check in a book that was checked out by userB and you will not be able to . You may the
log in as userA and view the checked out books and you will notice that the books checked out by userB
do not appear either.YOu can also try to check in a book that was checked out by userB and you will not
be able to  After this feel free to type help and check every function freely.


TLDR: type help and go from there!!!


Please login with the following credentials:
Username: admin
Password: admin123



