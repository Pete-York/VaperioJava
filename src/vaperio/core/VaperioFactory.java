package vaperio.core;

import ggi.core.AbstractGameFactory;
import ggi.core.AbstractGameState;

public class VaperioFactory implements AbstractGameFactory {
    private VaperioParams params;

    public VaperioFactory(VaperioParams params){
        this.params = params;
    }

    @Override
    public AbstractGameState newGame() {
        return new VaperioGameState(params);
    }
}
