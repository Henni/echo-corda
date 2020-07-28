package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.node.ServiceHub;
import net.corda.core.node.services.IdentityService;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.UntrustworthyData;

import java.util.Set;

// ******************
// * Initiator flow *
// ******************
@InitiatingFlow
@StartableByRPC
public class EchoInitiator extends FlowLogic<Void> {
    private final ProgressTracker progressTracker = new ProgressTracker();

    private String message;
    private String counterParty;

    public EchoInitiator(String message, String counterparty) {
        this.message = message;
        this.counterParty = counterparty;
    }

    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        // Initiator flow logic goes here.
        ServiceHub svcHub = getServiceHub();
        IdentityService idSvc = svcHub.getIdentityService();
        Set<Party> counterparties = idSvc.partiesFromName(counterParty, false);

        for (Party p : counterparties) {
            final FlowSession counterpartySession = initiateFlow(p);
            counterpartySession.send(message);
        }

        return null;
    }
}
