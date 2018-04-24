package spinbattle.actuator;

import ggi.AbstractGameState;
import spinbattle.core.Planet;
import spinbattle.core.SpinGameState;
import spinbattle.core.Transporter;


/**
 *
 *  Class for an AI controller.  This
 */

public class SourceTargetActuator implements Actuator {

    Integer planetSelected;
    int playerId;


    public SourceTargetActuator copy() {
        SourceTargetActuator copy = new SourceTargetActuator();
        copy.playerId = playerId;
        copy.planetSelected = planetSelected;
        return copy;
    }

    public SourceTargetActuator setPlayerId(int playerId) {
        this.playerId = playerId;
        return this;
    }

    public int nActions(SpinGameState spinGameState) {
        return spinGameState.planets.size();
    }

    public SpinGameState actuate(int action, SpinGameState gameState) {
        // if not already selected then select it
        // System.out.println(action);
        if (planetSelected == null) {
            Planet source = gameState.planets.get(action);
            if (source.transitReady() && source.ownedBy == playerId) {
                planetSelected = action;
            }
        } else {
            Planet source = gameState.planets.get(planetSelected);
            Planet target = gameState.planets.get(action);
            Transporter transit = source.getTransporter();
            // shift 50%
            transit.setPayload(source, source.shipCount / 2);
            transit.launch(source.position, target.position, playerId);
            transit.setTarget(action);
            planetSelected = null;
        }
        return gameState;
    }
}