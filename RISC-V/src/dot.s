.globl dot

.text
# =======================================================
# FUNCTION: Dot product of 2 int arrays
# Arguments:
#   a0 (int*) is the pointer to the start of arr0
#   a1 (int*) is the pointer to the start of arr1
#   a2 (int)  is the number of elements to use
#   a3 (int)  is the stride of arr0
#   a4 (int)  is the stride of arr1
# Returns:
#   a0 (int)  is the dot product of arr0 and arr1
# Exceptions:
#   - If the number of elements to use is less than 1,
#     this function terminates the program with error code 36
#   - If the stride of either array is less than 1,
#     this function terminates the program with error code 37
# =======================================================
dot:
    addi t1, x0, 0 # counter
    addi t2, x0, 0 # sum
    # Prologue
    addi t0, x0, 1
    bge a2, t0, check_stride1
    li a0, 36
    j exit
check_stride1:
    bge a3, t0, check_stride2
    li a0, 37
    j exit
check_stride2:
    bge a4, t0, loop_start
    li a0, 37
    j exit
loop_start:
    slli t3, t1, 2
    mul t4, t3, a3 # pointer to array0
    add t4, t4, a0
    mul t5, t3, a4 # pointer to array1
    add t5, t5, a1
    lw t0, 0(t4)
    lw t3, 0(t5)
    mul t0, t0, t3
    add t2, t2, t0
    addi t1, t1, 1
    bge t1, a2, loop_end
    j loop_start
loop_end:
    mv a0, t2
    # Epilogue


    jr ra
