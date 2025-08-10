.globl argmax

.text
# =================================================================
# FUNCTION: Given a int array, return the index of the largest
#   element. If there are multiple, return the one
#   with the smallest index.
# Arguments:
#   a0 (int*) is the pointer to the start of the array
#   a1 (int)  is the # of elements in the array
# Returns:
#   a0 (int)  is the first index of the largest element
# Exceptions:
#   - If the length of the array is less than 1,
#     this function terminates the program with error code 36
# =================================================================
argmax:
    # Prologue: checks if length is less than 1, exits if so, loops otherwise
    addi t0 x0 1
    bge a1 t0 loop_start
    li a0 36
    j exit

loop_start:
    li t2 -2147483648 #t1 represents 'max'
    addi t1 x0 0 #this is a counter for the element we're on, 4-indexed
	#slli t3 a1 2 #multiples elements by 4 to get byte count
    addi t5 x0 0 #counter 1-indexed
loop_continue:
    addi t3 a1 -1 ##subtracts a1 by 1 to get actual index
    add t4 a0 t1 #increment a0 by counter
    lw t6 0(t4)
    blt t2 t6 max
    j loop_cont
max:
    mv t2 t6
    mv t0 t5
    j loop_cont
    #bge t5 a1 loop_end
loop_cont:
    bge t5 t3 loop_end
    addi t1 t1 4
    addi t5 t5 1
    j loop_continue

loop_end:
    # Epilogue
    mv a0 t0
    ret
    jr ra
