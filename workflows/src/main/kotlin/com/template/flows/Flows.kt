package com.template.flows

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.*
import net.corda.core.utilities.ProgressTracker
import com.r3.corda.lib.accounts.workflows.accountService
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.StartableByRPC
import net.corda.core.utilities.getOrThrow

// *********
// * Flows *
// *********
@InitiatingFlow
@StartableByRPC
class CreateNewAccount(private val BeginNumber:Long,private val  LastNumber:Long) : FlowLogic<String>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call() : String{
        //Create a new account
        //Check arguments
        if ( BeginNumber > LastNumber ){
            return "argument error. Enter a value less than LastNumber for BeginNumber."
        }

        //Loop for Create Accounts
        for (i in BeginNumber..LastNumber) {
            val acctName = "TestAccount" + i.toString(10)
            // val newAccount = accountService.createAccount(name = acctName).toCompletableFuture().getOrThrow()

            try {
                accountService.createAccount(name = acctName).toCompletableFuture().getOrThrow()
            }catch (e: Exception){
                println(e)
                System.exit(1)
            }

        }

        return "accounts have created !"
    }
}
