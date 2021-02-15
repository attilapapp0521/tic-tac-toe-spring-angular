export interface GameInfoModel {
  board: string[];
  playerOne: boolean;
  coordinate: number;
  condition: number;
  wonP1: number;
  wonP2: number;
  draw: number;
  gameId: number;
}
