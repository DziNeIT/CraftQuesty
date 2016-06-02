// (the QuestLoader loads a quest as if it were already inside a function which returns a Quest type, with the variable questManager defined already)
var builder = QuestBuilder.begin(questManager, "KillPlayer").description("Kill any single player"); // create the quest builder and give it a name and description
var obj1 = builder.objective("Kill1Player").description("Kill a player"); // add the first objective to the quest, giving it a name and description
obj1.outcome("PlayerKilled").description("Killing a player").type("killplayer"); // add the only possible outcome to the objective, specifying name, description and type
return builder.build(); // return the built quest
// this file will create a quest which is completed as soon as the quester kills a single player in the game world