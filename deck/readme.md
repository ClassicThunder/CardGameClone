player combat start
enemy combat start

    round start
        before player turn
            player turn (can play cards)
        after player turn

        before enemy turn
            enemy turn
        after enemy turn
    round end

player combat end
enemy combat end


Intents are possible actions

Actions 
* apply effects
* add or remove status amount (status apply effects at end / beginning of turn)

Effects [Store effects as an array and loop through them?]
    Attack
    Block
    Poison
    