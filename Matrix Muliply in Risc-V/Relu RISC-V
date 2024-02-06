.globl relu

.text
# ==============================================================================
# FUNCTION: Performs an inplace element-wise ReLU on an array of ints
# Arguments:
#   a0 (int*) is the pointer to the array
#   a1 (int)  is the # of elements in the array
# Returns:
#   None
# Exceptions:
#   - If the length of the array is less than 1,
#     this function terminates the program with error code 36
# ==============================================================================
relu:
    # Prologue
    addi t6, x0, 1
    bge a1, t6, loop_pro
    li a0 36
    j exit
loop_pro:    
    addi t0, x0, 0 # counter
    slli t3, a1, 2
loop_start:
    add t1, t0, a0
    lw t2, 0(t1) # a[i]
    addi t0, t0, 4
loop_continue:
    bge t2, x0, loop_end
    sub t2, x0, x0
    sw t2, 0(t1)
loop_end:
    blt t0, t3, loop_start

    # Epilogue


    jr ra
