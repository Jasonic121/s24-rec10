interface GameState {
  cells: Cell[];
  winner: string | null;
  currentPlayer: string;
  nextTurn: string;
  history?: string;
}

interface Cell {
  text: string;
  playable: boolean;
  x: number;
  y: number;
}

export type { GameState, Cell }