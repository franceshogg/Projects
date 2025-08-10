.globl classify

.text
# =====================================
# COMMAND LINE ARGUMENTS
# =====================================
# Args:
#   a0 (int)        argc
#   a1 (char**)     argv
#   a1[1] (char*)   pointer to the filepath string of m0
#   a1[2] (char*)   pointer to the filepath string of m1
#   a1[3] (char*)   pointer to the filepath string of input matrix
#   a1[4] (char*)   pointer to the filepath string of output file
#   a2 (int)        silent mode, if this is 1, you should not print
#                   anything. Otherwise, you should print the
#                   classification and a newline.
# Returns:
#   a0 (int)        Classification
# Exceptions:
#   - If there are an incorrect number of command line args,
#     this function terminates the program with exit code 31
#   - If malloc fails, this function terminates the program with exit code 26
#
# Usage:
#   main.s <M0_PATH> <M1_PATH> <INPUT_PATH> <OUTPUT_PATH>
classify:
    #check that the number of arguments is 5, otherwise return arg_error
    li t0 5
    bne t0 a0 arg_error
    
    #prologue
    addi sp sp -60
    sw ra 0(sp)
    sw s0 4(sp)
    sw s1 8(sp)
    sw s2 12(sp)
    sw s3 16(sp)
    sw s4 20(sp)
    sw s5 24(sp)
    sw s6 28(sp)
    sw s7 32(sp)
    sw s8 36(sp)
    sw s9 40(sp)
    sw s10 44(sp)
    sw s11 48(sp)
    sw a6 52(sp)
    sw t4 56(sp)


    
    #save printval and arg pointer
    mv s0 a2 #saving a2 (print value) at s0
    mv s1 a1 #saving a1 (arg pointer) at s1 



    # Read pretrained m0
    li a0 8 #malloc space for a1, a2

    addi sp sp -4
    sw ra 0(sp)
    jal ra malloc
    lw ra 0(sp)
    addi sp sp 4

    beq a0 x0 malloc_error #check for malloc error
    mv s2 a0 #saves malloc ptr
    lw a0 4(s1) #gets a[1] (m0)
    mv a1 s2 #points to beggining of malloc (gets row)
    addi s3 s2 4
    mv a2 s3 #4-bytes into malloc (gets col)

    addi sp sp -4
    sw ra 0(sp)
    jal ra read_matrix
    lw ra 0(sp)
    addi sp sp 4

    #lw s2 0(s2) #saves rows of m0 to s2
    #lw s3 0(s3) #saves cols of m0 to s3
    mv s8 a0 #saves ptr to m0 



    # Read pretrained m1
    li a0 8 #malloc space for a1, a2

    addi sp sp -4
    sw ra 0(sp)
    jal ra malloc
    lw ra 0(sp)
    addi sp sp 4

    beq a0 x0 malloc_error #checks that malloc works 
    mv s4 a0 #saves malloc ptr
    lw a0 8(s1) #gets a[2] (m1)
    mv a1 s4 #points to beggining of malloc (gets row)
    addi s5 s4 4
    mv a2 s5 #4-bytes into malloc (gets col)

    addi sp sp -4
    sw ra 0(sp)
    jal ra read_matrix
    lw ra 0(sp)
    addi sp sp 4

    #lw s4 0(s4) #saves m1 rows to s4
    #lw s5 0(s5) #saves m1 cols to s4
    mv s9 a0 #saves ptr to m1 



    # Read input matrix
    li a0 8 #malloc space for a1, a2

    addi sp sp -4
    sw ra 0(sp)
    jal ra malloc
    lw ra 0(sp)
    addi sp sp 4

    beq a0 x0 malloc_error
    mv s6 a0 #saves malloc ptr
    lw a0 12(s1) #gets a[3] (input)
    mv a1 s6 #points to begging of malloc (gets row)
    addi s7 s6 4
    mv a2 s7 #4-bytes into malloc (gets col)

    addi sp sp -4
    sw ra 0(sp)
    jal ra read_matrix
    lw ra 0(sp)
    addi sp sp 4

    #lw s6 0(s6) #saves input rows to s6
    #lw s7 0(s7) #saves input cols to s7
    mv s10 a0 #saves ptr to input



    # Compute h = matmul(m0, input)
    lw t0 0(s2) #t0 = m0 rows
    lw t1 0(s7) #t1 = input cols
    mul t6 t0 t1 #m0 rows * input cols, this gets the size of h
    slli a0 t6 2 #a0 = size of h * 4

    addi sp sp -4
    sw ra 0(sp)
    jal ra malloc 
    lw ra 0(sp)
    addi sp sp 4

    beq a0 x0 malloc_error
    mv t6 a0 #t6 is pointer to malloced space 
    
    mv a0 s8 #pointer to m0
    lw t0 0(s2) #m0 rows
    mv a1 t0 #a1 = rows of m0
    lw t0 0(s3) #gets cols of m0
    mv a2 t0 #a2 = cols of m0
    mv a3 s10 #pointer to input
    lw t0 0(s6) #rows of input
    mv a4 t0 #a4 = rows of input
    lw t0 0(s7) #get cols of input
    mv a5 t0 #sets a5 to cols of input
    mv a6 t6 #a6 = malloced space
    
    addi sp sp -4 #SAVING TO FREE
    sw t6 0(sp) #SAVING TO FREE

    # changed
    addi sp sp -4
    sw a6 0(sp) #saving 'h'

    bne a2 a4 malloc_error

    addi sp sp -4
    sw ra 0(sp)
    jal matmul
    lw ra 0(sp)
    addi sp sp 4

    #sw a6 0(sp) #saving 'h'

    lw a6 0(sp) #retrieving 'h'
    addi sp sp 4


    # Compute h = relu(h)
    # lw t0 0(sp) #retrieving 'h'
    mv t0 a6
    # addi sp sp 4
    mv a0 t0 #ptr to h
    lw t0 0(s2) #t0 = m0 rows
    lw t1 0(s7) #t1 = input cols
    mul a1 t0 t1 #size of h (m0 rows * input cols)

    mv s11 a0 #saving h again

    addi sp sp -4
    sw ra 0(sp)
    jal ra relu
    lw ra 0(sp)
    addi sp sp 4

    # mv s11 a0 #saving h again



    # Compute o = matmul(m1, h)
    #get number of rows of m1 and number of columns of h
    lw t0 0(s4) #t0 = m1 rows
    lw t1 0(s7) #t1 = input cols
    mul t6 t0 t1 #m1 rows * h cols, this gets the size of o
    slli a0 t6 2

    addi sp sp -4
    sw ra 0(sp)
    jal ra malloc
    lw ra 0(sp)
    addi sp sp 4

    beq a0 x0 malloc_error
    mv t6 a0 #pointer to malloced space 
    
    addi sp sp -4
    sw t6 0(sp)
    
    mv a0 s9 #pointer to m1
    lw t0 0(s4) #t0 = m1 rows
    mv a1 t0 #rows of m1
    lw t0 0(s5) #t0 = m1 cols
    mv a2 t0 #cols of m1
    mv t0 s11
    mv a3 t0 #pointer to h
    lw t0 0(s2) #t0 = m0 rows
    mv a4 t0 #rows of h
    lw t0 0(s7) #t0 = input cols
    mv a5 t0 #cols of h
    mv a6 t6

    addi sp sp -4
    sw a6 0(sp) #saving 'o'

    addi sp sp -4
    sw ra 0(sp)
    jal matmul
    lw ra 0(sp)
    addi sp sp 4

    
    lw t0 0(sp)
    addi sp sp 4

    addi sp sp -4
    sw s0 0(sp)
    mv s0 t0 #saving o



    # Write output matrix o
    lw a0 16(s1) #a[4] (output file)
    mv a1 s0 #ptr to o

    #      lw s0 0(sp)
    #      addi sp sp 4

    lw t0 0(s4)
    mv a2 t0 #rows
    lw t0 0(s7)
    mv a3 t0 #columns

    addi sp sp -4
    sw ra 0(sp)
    jal ra write_matrix
    lw ra 0(sp)
    addi sp sp 4



    # Compute and return argmax(o)
    mv a0 s0 #pointer to 'o' #t4 -> s0
    lw t0 0(s4)
    lw t1 0(s7)
    mul a1 t0 t1 #size of 'o'
    
    addi sp sp -4
    sw ra 0(sp)
    jal ra argmax
    lw ra 0(sp)
    addi sp sp 4

    mv s3 a0 #return value



    # If enabled, print argmax(o) and newline
    lw s0 0(sp)
    addi sp sp 4
    bne s0 x0 finish

    addi sp sp -4
    sw ra 0(sp)
    jal ra print_int #prints return value of argmax
    lw ra 0(sp)
    addi sp sp 4

    li a0 '\n'

    addi sp sp -4
    sw ra 0(sp)
    jal ra print_char
    lw ra 0(sp)
    addi sp sp 4
finish: 
    #free space
    mv a0 s8
    jal free
    
    mv a0 s9 
    jal free
    
    mv a0 s10
    jal free
    
    lw a0 0(sp)
    jal free
    addi sp sp 4
    
    lw a0 0(sp)
    jal free 
    addi sp sp 4
    
    mv a0 s2
    jal free
    mv a0 s4

    addi sp sp -4
    sw ra 0(sp)
    jal free
    lw ra 0(sp)
    addi sp sp 4

    mv a0 s6

    addi sp sp -4
    sw ra 0(sp)
    jal free
    lw ra 0(sp)
    addi sp sp 4

    #mv a0 s3 #t6 -> s3
    #jal ra free
    
    #return value
    mv a0 s3 #t6 -> s3
    
    #free stack
    lw ra 0(sp)
    lw s0 4(sp)
    lw s1 8(sp)
    lw s2 12(sp)
    lw s3 16(sp)
    lw s4 20(sp)
    lw s5 24(sp)
    lw s6 28(sp)
    lw s7 32(sp)
    lw s8 36(sp)
    lw s9 40(sp)
    lw s10 44(sp)
    lw s11 48(sp)
    lw a6 52(sp)
    lw t4 56(sp)
    addi sp sp 60
    
    ret
arg_error:
    li a0 31
    j exit
malloc_error:
    li a0 26
    j exit