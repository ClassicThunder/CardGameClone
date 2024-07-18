package classicthunder.combat

class StateMachine(startingState: State) {

    var currentState: State = startingState
        private set
    private var desiredState: State = startingState

    init {
        currentState.enter()
    }

    fun update() {
        if (desiredState != currentState) {

            currentState.exit()
            currentState = desiredState
            currentState.enter()
        }

        desiredState = currentState.update()
    }
}
