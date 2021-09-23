# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
#
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        curFood = currentGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** CS3568 YOUR CODE HERE ***"
        "Decribe your function:"

        # 对这个选择评估的分数
        currscore = 0

        if action == "Stop":
            return -100

        # 如果当前状态能够使ghost害怕，将所有的时间加入进来
        for st in newScaredTimes:
            currscore += st

        # 根据Ghost所在的位置，获取与当前位置的距离
        ghost_distances = []
        for gs in newGhostStates:
            ghost_distances += [manhattanDistance(gs.getPosition(),newPos)]

        # 获取food所在的所有pos
        foodList = newFood.asList()
        curfoodList = curFood.asList()

        # 获取food所在的所有wall
        wallList = currentGameState.getWalls().asList()

        # 保存food的距离
        food_distences = []

        # 获取所有食物到达当前位置的距离
        for foodpos in foodList:
            food_distences += [manhattanDistance(newPos,foodpos)]

        # 对食物的距离取反
        inverse_food_distences=0
        if len(food_distences)>0 and min(food_distences) > 0:
            inverse_food_distences = 1.0 / min(food_distences)
        # 考虑了ghost与当前的距离，其权值更大
        currscore += min(ghost_distances)*(inverse_food_distences**4)
        # 获取当前系统判定的分数 又可能当前吃到了豆子 分数更高些
        currscore+=successorGameState.getScore()
        if newPos in curfoodList:
            currscore = currscore * 1.1
        return currscore

        return successorGameState.getScore()

def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** CS3568 YOUR CODE HERE ***"
        "PS. It is okay to define your own new functions. For example, value, min_function,max_function"
        #初始化
        maxVal = -float('inf')
        bestAction = None
        # 从吃豆人最初的位置，遍历所有可行的下一步
        for action in gameState.getLegalActions(0):
            value = self._getMin(gameState.generateSuccessor(0, action))
            if value is not None and value > maxVal:
                maxVal = value
                bestAction = action
        # 最后返回最佳选择
        return bestAction
    def _getMax(self, gameState, depth=0, agentIndex=0):
        # 获得吃豆人下一步所有合法的操作
        legalActions = gameState.getLegalActions(agentIndex)
        # 终止条件
        if depth == self.depth or len(legalActions) == 0:
            return self.evaluationFunction(gameState)
        maxVal = -float('inf')
        for action in legalActions:
            # 从第一个鬼怪开始遍历
            value = self._getMin(gameState.generateSuccessor(agentIndex, action), depth, 1)
            if value is not None and value > maxVal:
                maxVal = value
        return maxVal

    def _getMin(self, gameState, depth=0, agentIndex=1):
        # 获得鬼怪们的下一步合法操作
        legalActions = gameState.getLegalActions(agentIndex)
        # 终止条件
        if depth == self.depth or len(legalActions) == 0:
            return self.evaluationFunction(gameState)
        minVal = float('inf')
        # 遍历
        for action in legalActions:
            # 如果当前已经是最后一只鬼怪，那么下一轮就该是计算吃豆人的行为了，即调用MAX函数
            if agentIndex == gameState.getNumAgents() - 1:
                value = self._getMax(gameState.generateSuccessor(agentIndex, action), depth + 1, 0)
            else:
                value = self._getMin(gameState.generateSuccessor(agentIndex, action), depth, agentIndex + 1)
            if value is not None and value < minVal:
                minVal = value
        return minVal
        util.raiseNotDefined()

class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** CS3568 YOUR CODE HERE ***"
        "PS. It is okay to define your own new functions. For example, value, min_function,max_function"
        return self._getMax(gameState)[1]

    def _getMax(self, gameState, depth=0, agentIndex=0, alpha=-float('inf'),
                beta=float('inf')):
        # 终止条件
        legalActions = gameState.getLegalActions(agentIndex)
        if depth == self.depth or len(legalActions) == 0:
            return self.evaluationFunction(gameState), None
        # 遍历吃豆人可能的下一步
        maxVal = None
        bestAction = None
        for action in legalActions:
            # 遍历所有鬼怪
            value = self._getMin(gameState.generateSuccessor(agentIndex, action), depth, 1, alpha, beta)[0]
            if value is not None and (maxVal == None or value > maxVal):
                maxVal = value
                bestAction = action
            # 按照α-β剪枝算法，如果v>β，则直接返回v
            if value is not None and value > beta:
                return value, action
            # 按照α-β剪枝算法，这里还需要更新α的值
            if value is not None and value > alpha:
                alpha = value
        return maxVal, bestAction

    def _getMin(self, gameState, depth=0, agentIndex=0, alpha=-float('inf'),
                beta=float('inf')):
        # 终止条件
        legalActions = gameState.getLegalActions(agentIndex)
        if depth == self.depth or len(legalActions) == 0:
            return self.evaluationFunction(gameState), None
        # 遍历当前鬼怪可能的下一步
        minVal = None
        bestAction = None
        for action in legalActions:
            if agentIndex >= gameState.getNumAgents() - 1:
                # 与minimax不同多了α和β的值
                value = self._getMax(gameState.generateSuccessor(agentIndex, action), depth + 1, 0, alpha, beta)[0]
            else:
                # 如果不是最后一个鬼怪，则继续遍历下一个鬼怪，即agentIndex+1
                value = \
                self._getMin(gameState.generateSuccessor(agentIndex, action), depth, agentIndex + 1, alpha, beta)[0]
            if value is not None and (minVal == None or value < minVal):
                minVal = value
                bestAction = action
            # 按照α-β剪枝算法，如果v<α，则直接返回v
            if value is not None and value < alpha:
                return value, action
            # 按照α-β剪枝算法，这里还需要更新β的值
            if value is not None and value < beta:
                beta = value
        return minVal, bestAction
        util.raiseNotDefined()

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** CS3568 YOUR CODE HERE ***"
        "PS. It is okay to define your own new functions. For example, value, min_function,max_function"
        return self._getMax(gameState)

    def _getMax(self, gameState, depth=0, agentIndex=0):
        # 获得吃豆人所有合法的下一步行动
        legalActions = gameState.getLegalActions(agentIndex)
        # 终止条件
        if depth == self.depth or len(legalActions) == 0:
            return self.evaluationFunction(gameState)
        # 初始化
        maxVal = None
        bestAction = None
        for action in legalActions:
            # 从第一个鬼怪开始，进行Expectimax操作
            value = self._getExpectation(gameState.generateSuccessor(agentIndex, action), depth, 1)
            if value is not None and (maxVal == None or value > maxVal):
                maxVal = value
                bestAction = action
        if depth is 0 and agentIndex is 0:
            return bestAction
        else:
            return maxVal

    def _getExpectation(self, gameState, depth=0, agentIndex=0):
        legalActions = gameState.getLegalActions(agentIndex)
        # 终止条件
        if depth == self.depth or len(legalActions) == 0:
            return self.evaluationFunction(gameState)
            # 初始化效用值总计
        totalUtil = 0
        numActions = len(legalActions)
        # 轮询当前鬼怪所有可行的下一步
        for action in legalActions:
            # 同样，如果是最后一个鬼怪，那么接下来要去算吃豆人的MAX值，并计入效用总计
            if agentIndex >= gameState.getNumAgents() - 1:
                totalUtil += self._getMax(gameState.generateSuccessor(agentIndex, action), depth + 1, 0)
            # 否则，挨个遍历各个鬼怪，计算Expectation值，并计入效用总计
            else:
                totalUtil += self._getExpectation(gameState.generateSuccessor(agentIndex, action), depth,
                                                  agentIndex + 1)
        # 最后需要把所有可能的下一步的效用值求平均，并返回
        return totalUtil / float(numActions)
        util.raiseNotDefined()

def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** CS3568 YOUR CODE HERE ***"
    newPos = currentGameState.getPacmanPosition()
    newFood = currentGameState.getFood()
    newGhostStates = currentGameState.getGhostStates()

    # Consts
    INF = 100000000.0  # Infinite value
    WEIGHT_FOOD = 10.0  # Food base value
    WEIGHT_GHOST = -10.0  # Ghost base value
    WEIGHT_SCARED_GHOST = 100.0  # Scared ghost base value

    # Base on gameState.getScore()
    score = currentGameState.getScore()

    # 最近的豆子的距离评估
    distancesToFoodList = [util.manhattanDistance(newPos, foodPos) for foodPos in newFood.asList()]
    if len(distancesToFoodList) > 0:
        score += WEIGHT_FOOD / min(distancesToFoodList)
    else:
        score += WEIGHT_FOOD

    # ghosts距离评估
    for ghost in newGhostStates:
        distance = manhattanDistance(newPos, ghost.getPosition())
        if distance > 0:
            if ghost.scaredTimer > 0:  # If scared, add points
                score += WEIGHT_SCARED_GHOST / distance
            else:  # If not, decrease points
                score += WEIGHT_GHOST / distance
        else:
            return -INF  # Pacman is dead at this point

    return score
    util.raiseNotDefined()

# Abbreviation
better = betterEvaluationFunction
