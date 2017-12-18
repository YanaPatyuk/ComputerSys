	.data
tamp:   .string "this is my first print\n"
check:  .string "secsses in case!!!! \n"
doneN:  .string "done\n"

    .section	.rodata	#read only data section
    .text	#beginning of the code:

.global ‪pstrlen
    .type ‪pstrlen, @function
‪pstrlen:
          pushq	%rbx		# we need a save resiter to save the return address to the main function.
          movb (%rdi), %al        #in the first memory we have value of langth.
          popq	%rbx		#restoring the save register (%rbx) value, for the caller function.          
          ret			#return to caller function (main)

.global replaceChar
    .type replaceChar, @function
    #rdi is pointer to pstring, rsi is char old rdx is new
replaceChar:
            pushq	%rbp		# we need a save resiter to save the return address to the main function.
            movq %rsp, %rbp
            movq %rdi, %rsp #saving stirng pointer
            xorq %rax, %rax
            cmpb %al, %dil
            je .done1
         .loop:
            movq $0, %rcx
            incb %spl
            movb (%rsp), %cl
            cmpb %cl, %sil
            je .change
            cmpb %al, (%rsp)
            je .done1
            jmp .loop 
        .change:
            movb %dl, (%rsp)
            jmp .loop
        .done1:
            movq    %rdi, %rax
            movq %rbp, %rsp
            popq	%rbp		#restoring the save register (%rbx) value, for the caller function.          
            ret 
            
            
            
 .global pstrijcpy
    .type pstrijcpy, @function
     #rdi is pointer to dst, rsi is pointer to src, rdx is i, rcx j
pstrijcpy:
            pushq	%rbp		# we need a save resiter to save the return address to the main function.
            movq %rsp, %rbp
            push %rdi
            movq %rdi, %rsp
            movq $0, %rax
        .loop3:
            incb (%rax)
            incb %dil
            incb %sil
            cmpb %al, %dl
            je .startChange
            cmpb $0, %dil
            je .done5
            jmp .loop3
            
        .startChange:
            movb %sil, (%rdi)
            incb (%rax)
            incb %dil
            incb %sil
            cmpb %al, %cl
            je .done5
            jmp .startChange
        .done5:
            pop %rax
            movq %rbp, %rsp
            popq	%rbp		#restoring the save register (%rbx) value, for the caller function.          
            ret 
        
 .global ‫‪swapCase‬‬
    .type ‫‪swapCase‬‬, @function
‪swapCase‬‬:
        pushq	%rbx		# we need a save resiter to save the return address to the main function.
        movq %rdi, %rax
        .loop1:
            incq    %rdi
          #  cmpq (%rdi), $0x41
        .changSmall:
        .changeBig:
        .done:
            popq	%rbx		#restoring the save register (%rbx) value, for the caller function.      
            ret    
