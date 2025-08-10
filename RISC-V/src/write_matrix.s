.globl write_matrix

.text
# ==============================================================================
# FUNCTION: Writes a matrix of integers into a binary file
# FILE FORMAT:
#   The first 8 bytes of the file will be two 4 byte ints representing the
#   numbers of rows and columns respectively. Every 4 bytes thereafter is an
#   element of the matrix in row-major order.
# Arguments:
#   a0 (char*) is the pointer to string representing the filename
#   a1 (int*)  is the pointer to the start of the matrix in memory
#   a2 (int)   is the number of rows in the matrix
#   a3 (int)   is the number of columns in the matrix
# Returns:
#   None
# Exceptions:
#   - If you receive an fopen error or eof,
#     this function terminates the program with error code 27
#   - If you receive an fclose error or eof,
#     this function terminates the program with error code 28
#   - If you receive an fwrite error or eof,
#     this function terminates the program with error code 30
# ==============================================================================
write_matrix:

    # Prologue
    addi sp sp -24
    sw s0 0(sp)
    sw s1 4(sp)
    sw s2 8(sp)
    sw s3 12(sp)
    sw s4 16(sp)
    sw s5 20(sp)
    
    #save inputs
    mv s0 a0
    mv s1 a1
    mv s2 a2
    mv s3 a3
    


    #open file
    li a1 1 #write-only

    addi sp sp -4
    sw ra 0(sp)
    jal ra, fopen #returns integer referring to a0 opened
    lw ra 0(sp) 
    addi sp sp 4

    addi t0, x0, -1 #t0 = -1
    beq a0 t0, fopen_error #if a0 is -1, then fopen failed

    mv s4 a0 #s4 = a0 (file descriptor)
    mv a1 s1 #retrive
    mv a2 s2 #retrive
    mv a3 s3 #retrive



    #write row into file
    mv a0 s4 #file descriptor
    addi sp sp -4
    sw s2 0(sp) #saves s2 (a2) into memory (fwrite expects pointer to data in memory)
    mv a1 sp #pointer to data
    li a2 1 #number of elements to read
    li a3 4 #byte-size

    mv s5 a2 #save a2
    
    addi sp, sp, -4
    sw ra, 0(sp)
    jal ra fwrite #puts rows in s2
    lw ra, 0(sp)
    addi sp, sp, 4

    bne a0 s5, fwrite_error #if a0 != s5, then fwrite failed
    mv a0 s4 #file descriptor
    mv a1 s1
    mv a2 s2
    mv a3 s3
    addi sp sp 4


    
    #write col into file
    mv a0 s4 #file descriptor
    addi sp sp -4
    sw s3 0(sp) #saves s3 (a3) into memory (fwrite expects pointer to data in memory)
    mv a1 sp #pointer to data
    li a2 1 #number of elements to read
    li a3 4 #byte-size

    mv s5 a2 #save a2
    
    addi sp, sp, -4
    sw ra, 0(sp)
    jal ra fwrite #puts rows in s2
    lw ra, 0(sp)
    addi sp, sp, 4

    bne a0 s5, fwrite_error #if a0 != s5, then fwrite failed
    mv a0 s4 #file descriptor
    mv a1 s1
    mv a2 s2
    mv a3 s3
    addi sp sp 4


    
    #write data into file
    mv a0 s4
    mv a1 s1
    mul s5 s2 s3 #gets total elements
    mv a2 s5
    li a3 4
    
    addi sp, sp, -4
    sw ra, 0(sp)
    jal ra fwrite #puts rows in s2
    lw ra, 0(sp)
    addi sp, sp, 4

    bne a0 s5, fwrite_error #if a0 != s5, then fwrite failed
    mv a0 s4 #file descriptor  
    mv a1 s1
    mv a2 s2
    mv a3 s3


    
    #close
    mv a0 s4
    addi sp, sp, -4
    sw ra, 0(sp)
    jal ra, fclose
    lw ra, 0(sp)
    addi sp, sp, 4

    bne a0, x0, fclose_error #if a0 is not equal to 0, then fclose failed



    # Epilogue
    lw s0 0(sp)
    lw s1 4(sp)
    lw s2 8(sp)
    lw s3 12(sp)
    lw s4 16(sp)
    lw s5 20(sp)
    addi sp sp 24
    jr ra

fopen_error:
    li a0 27
    j exit

fwrite_error:
    li a0 30
    j exit

fclose_error:
    li a0 28
    j exit