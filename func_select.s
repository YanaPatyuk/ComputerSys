#%include "io.inc"
	.data
tamp:   .string "‫‪first‬‬ ‫‪pstring‬‬ ‫‪length:‬‬ ‫‪%d,‬‬ ‫‪second‬‬ ‫‪pstring‬‬ ‫‪length:‬‬ ‫‪%d\n‬"
tamp2:  .string "‫‪old‬‬ ‫‪char:‬‬ ‫‪%c,‬‬ ‫‪new‬‬ ‫‪char:‬‬ ‫‪%c,‬‬ ‫‪first‬‬ ‫‪string:‬‬ ‫‪%s,‬‬ ‫‪second‬‬ ‫‪string:‬‬ ‫‪%s\n‬‬"
tamp5:  .string "‫‪length:‬‬ ‫‪%d,‬‬ ‫‪string:‬‬ ‫‪%s\n‬‬"
tamp3:  .string "this is answer: %s mmm %s\n"
doneN:  .string "done\n"
check:  .string "5defal  %s m\n"
char:    .string " %c"
int_r:      .string "%d"
char2:      .string "no not good \n"
x:      .string   "7aaaaaaaa"
y:      .string    "6bbbbbb"
#char:   .byte   
    .section	.rodata	#read only data section
    .align 8
    .text	#beginning of the code:

.global main
    .type main, @function
main:
        movq %rsp, %rbp #for correct debugging
        movq %rsp, %rbp #for correct debugging
        pushq	%rbx		#save value of callee-saved register         
        movq $52, %rdi
        movq $x, %rsi
        movq $y, %rdx
        call run_func
        movq	$0,	%rax	#return value is zero.
        popq	%rbx		#restore callee-saved register	
        ret

.global run_func
    .type run_func, @function
run_func:
      pushq	%rbx		# we need a save resiter to save the return address to the main function.
          # Set up the jump table access
      leaq -50(%rdi),%rax       	# Compute xi = x-50
      cmpq $4,%rax                     # Compare xi:4
      ja .DefMission 	          # if >, goto default-case
      jmp *.Switch_Case(,%rax,8)	# Goto jt[xi]
   # movel %rdi, %eax          #get n
.Switch_Case:
     .quad .FirstMission        #case 50
     .quad .SecondMission       #case 51
     .quad .ThirdMission        #case 52
     .quad .ForthMission        #case 53
     .quad .DefMission
     
      
      #case 50
.FirstMission:
        movq      %rsi, %rdi
  #     call       ‪pstrlen
        push      %rax
        movq      %rdx, %rdi
   #     call       ‪pstrlen
        push      %rax
        pop       %rdx
        pop       %rsi
        movq	$tamp,%rdi	#passing the string the first parameter for printf.
        movq	$0,%rax
        call	printf		#calling printf.
        movq	$0, %rax	        #return value is zero.
        jmp .done
        
      #case 51
.SecondMission:
        push %rsi
        push %rdx
        movq $0, %r12
        push %r12
        movq $char, %rdi  #move char to first param scanf
        movq %rsp, %rsi # move mem to second param
        movq $0, %rax
        call scanf
        movq $0, %rax
        pop %r12
        movb $0, %bl
        movb %r12b, %bl #store firat char 
        
        #second char
        movq $0, %r10
        push %r10
        movq $0, %rdi
        movq $char, %rdi  #move char to first param scanf
        movq %rsp, %rsi # move mem to second param
        movq $0, %rax
        call scanf
        pop %r10
        movb %r10b, %al
        
        #print
        movq $0, %rcx
        movb %bl, %sil
        movq $0, %rdx
        movb %al, %dl
        movq $0, %rax
        pop %rdi
      #  call replaceChar
        pop %rdi
        push %rax
        movq $0, %rax
       # call replaceChar
        movq %rax, %rcx
        pop %r8
        incb %cl
        incb %r8b
        movq $tamp2, %rdi
        movq $0, %rax
        call printf
        jmp .done
        
      #case 52
.ThirdMission:
        push %rsi
        push %rdx
        #leaq -1(%rsp), %rsp
        movq $0, %r12
        push %r12
        movq $int_r, %rdi  #move char to first param scanf
        movq %rsp, %rsi # move mem to second param
        movq $0, %rax
        call scanf
        movq $0, %rax
        pop %r12
        movq $0, %rbx
        movb %r12b, %bl #store firat char 
        
        #second char
        movq $0, %r10
        push %r10
        movq $0, %rdi
        movq $int_r, %rdi  #move char to first param scanf
        movq %rsp, %rsi # move mem to second param
        movq $0, %rax
        call scanf
        movq $0, %rax
        pop %r10
        movb %r10b, %al
        
        pop %rsi
        pop %rdi
        movq $0, %rcx
        movq $0, %rdx
        movq %rax, %rcx
        movq %rbx, %rdx
        
        call pstrijcpy

      /*  movq %rax, %rdx
        movq $0, %rsi
        movb %al, %sil
        incb %dl
        movq $tamp5, %rdi*/
        movq $0, %rsi
        movq %rax, %rsi
        movq $check, %rdi
        
       /* pop %rdi
        pop %rsi
        movq %rbx, %rdx
        movq %rax, %rcx
        call pstrijcpy
        movq $0, %rsi
        movb (%rax), %sil
        incb %al
        movq %rax, %rdx
        movq $tamp5, %rdi*/
        movq $0, %rax
        call printf
        movq $0, %rax
        
        pop %rdi
        pop %rsi
        jmp .done
        
      #case 53
.ForthMission:
        jmp .done
      #case def:
.DefMission:
      	movq	$check,%rdi	#passing the string the first parameter for printf.
	movq	$0,%rax
	call	printf		#calling printf.
	movq	$0, %rax	         #return value is zero.
        jmp .done
        
      # done:
.done:
      	movq	$doneN,%rdi	#passing the string the first parameter for printf.
	movq	$0,%rax
	call	printf		#calling printf.
	movq	$0, %rax	         #return value is zero.
	popq	%rbx		#restoring the save register (%rbx) value, for the caller function.          
          ret			#return to caller function (main)
      
 #pstring file     
/**	.data
    .section	.rodata	#read only data section
    .text	#beginning of the code:

.global ‪pstrlen
    .type ‪pstrlen, @function
‪pstrlen:
          movq (%rdi), %rax         #in the first memory we have value of langth.
          ret			#return to caller function (main)
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
            movb %dl, %dil
            jmp .loop
        .done1:
            movq    %rdi, %rax
            movq %rbp, %rsp
            popq	%rbp		#restoring the save register (%rbx) value, for the caller function.          
            ret 
            */
            
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
           # push %rdi
            movq %rdi, %rsp
            movq $0, %rax
            cmpb $0, %cl
            jl .done6        # j < 0
            cmpb $0, %dl
            jl .done6        # i < 0
            cmpb (%rdi), %cl    
            jg .done6
            cmpb (%rdi), %dl
            jg .done6
            cmpb (%rsi), %cl    
            jg .done6
            cmpb (%rsi), %dl
            jg .done6
        .loop3:
            incb %spl
            incb %sil
            cmpb %al, %dl
            je .startChange
            incq %rax
            cmpb $0, %spl
            je .done5
            jmp .loop3
            
        .startChange:
            movq $0, %rbx
            movb (%rsi), %bl
            movb %bl, (%rsp)
            cmpb %al, %cl
            je .done5
            incq %rax
            incb %spl
            incb %sil
            jmp .startChange
         .done6:
             movq $0, %rdi
             movq $char2, %rdi
             movq $0, %rax
             call printf
             movq $0, %rax
             movq %rsp, %rdi
             jmp .done5
        .done5:
            movq $0, %rax
            movq %rdi, %rax
            movq %rbp, %rsp
            popq	%rbp		#restoring the save register (%rbx) value, for the caller function.          
            ret
            