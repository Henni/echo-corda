package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.UntrustworthyData;

// ******************
// * Initiator flow *
// ******************
@InitiatingFlow
@StartableByRPC
public class EchoInitiator extends FlowLogic<Void> {
    private final ProgressTracker progressTracker = new ProgressTracker();

    private String message;
    private Party counterparty;

    public EchoInitiator(String _message, Party _counterparty) {
        message = _message;
        counterparty = _counterparty;
    }

    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        // Initiator flow logic goes here.
        final FlowSession counterpartySession = initiateFlow(counterparty);
        counterpartySession.send(message);

        return null;
    }
}
