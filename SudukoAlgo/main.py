import json
from random import randint, shuffle
from copy import deepcopy

def has_unique_solution(grid):
    solutions = solveGrid(deepcopy(grid), count=True)
    return solutions == 1


def checkGrid(grid):
    for row in grid:
        for cell in row:
            if cell == 0:
                return False
    return True

def solveGrid(grid, count=False):
    solutions = 0  # Initialize solution count
    for i in range(81):
        row = i // 9
        col = i % 9
        if grid[row][col] == 0:
            for value in range(1, 10):
                # Check if value is valid for the current cell
                if value not in grid[row]:
                    if value not in [grid[j][col] for j in range(9)]:
                        square = [grid[j][m] for j in range(row // 3 * 3, row // 3 * 3 + 3) for m in
                                  range(col // 3 * 3, col // 3 * 3 + 3)]
                        if value not in square:
                            grid[row][col] = value
                            
                            if checkGrid(grid):
                                if count:  # If counting solutions, increment the solution count
                                    solutions += 1
                                else:  # If not counting, simply return True
                                    return True
                            else:
                                # Recursively try to fill the rest of the grid
                                result = solveGrid(grid, count)
                                if count:
                                    solutions += result  # Increment solution count
                                elif result:
                                    return True  # Return True if a solution is found
                            
            # Backtrack
            grid[row][col] = 0
            break  # Break out of the loop as this grid is not solvable
    
    if count:
        return solutions  # Return the number of solutions if counting
    else:
        return False  # Return False if the grid is not solvable


def fillGrid(grid):
    for i in range(81):
        row = i // 9
        col = i % 9
        if grid[row][col] == 0:
            numbers = list(range(1, 10))
            shuffle(numbers)
            for value in numbers:
                if value not in grid[row]:
                    if value not in [grid[j][col] for j in range(9)]:
                        square = [grid[j][m] for j in range(row//3*3, row//3*3 + 3) for m in range(col//3*3, col//3*3 + 3)]
                        if value not in square:
                            grid[row][col] = value
                            if checkGrid(grid):
                                return True
                            else:
                                if fillGrid(grid):
                                    return True
            break
    grid[row][col] = 0
    return False

def remove_clues(grid, num_clues_to_remove):
    modified_grid = deepcopy(grid)
    removed_clues = 0

    while removed_clues < num_clues_to_remove:
        row = randint(0, 8)
        col = randint(0, 8)
        while modified_grid[row][col] == 0:
            row = randint(0, 8)
            col = randint(0, 8)

        backup = modified_grid[row][col]
        modified_grid[row][col] = 0

        grid_copy = deepcopy(modified_grid)

        # This function should return True if the grid has a unique solution, otherwise False
        if has_unique_solution(grid_copy):
            removed_clues += 1
        else:
            modified_grid[row][col] = backup

    return modified_grid

if __name__ == '__main__':
    grid = [[0 for _ in range(9)] for _ in range(9)]
    fillGrid(grid)
    
    #easy_board = remove_clues(grid, 40)
    #medium_board = remove_clues(grid, 50)
    hard_board = remove_clues(grid, 55)

    with open('sudoku_boards.json', 'w') as f:
        json.dump({
            #'easy': easy_board,
            #'medium': medium_board,
            'hard': hard_board
        }, f)

    print("Sudoku Boards Ready")



