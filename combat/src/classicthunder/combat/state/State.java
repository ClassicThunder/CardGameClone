package classicthunder.combat.state;

public interface State {

    public abstract void Enter();

    public abstract EngineState Update();

    public abstract void Exit();
}
