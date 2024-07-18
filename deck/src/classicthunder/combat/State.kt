package classicthunder.combat

interface State {
    fun enter()
    fun update(): State
    fun exit()
}