import {Component, OnInit} from '@angular/core';
import {GameInfoModel} from "../../_models/game-info.model";
import {GameService} from "../../_services/game.service";

@Component({
  selector: 'app-playground',
  templateUrl: './playground.component.html',
  styleUrls: ['./playground.component.css']
})
export class PlaygroundComponent implements OnInit{
  gameForm: GameInfoModel;

  constructor(private gameService: GameService) { }

  ngOnInit(): void {

  }


  newGame(){
    this.gameService.getNewGame().subscribe(
      response =>{
        this.gameForm = response;
        console.log(response);
      }
    )
  }

  move(step: number){

    if(!this.gameForm.board[step] && !this.isWin() &&
      !this.draw()){
      this.gameForm.coordinate =  step;
      this.gameService.postMove(this.gameForm).subscribe(
        response =>{
          this.gameForm = response;
        }
      )
    }
  }

  isWin(): boolean{
    if(this.gameForm === undefined){
      return false;
    }
    else if((this.gameForm.condition === 1 || this.gameForm.condition === 2)){
      return true;
    }
    return false;
  }

  isGame() : boolean{
    if(this.gameForm == undefined){
      return false
    }else if(this.gameForm.condition === 0){
      return true;
    }
    return false;
  }

  player(): string{
    if(this.gameForm?.playerOne){
      return 'Player 1';
    }
    return 'Player 2';
  }

  draw(): boolean{
    if(this.gameForm === undefined){
      return false;
    }
    else if(this.gameForm.condition === 3){
      return true;
    }
    return false;
  }

}
