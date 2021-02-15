import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GameInfoModel} from "../_models/game-info.model";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class GameService {
  baseUrl = environment.apiUrl + '/game'

  constructor(private http: HttpClient) { }

  getNewGame() {
    return this.http.get<GameInfoModel>(this.baseUrl);
  }


  postMove(gameForm: GameInfoModel) {
    return this.http.post<GameInfoModel>(this.baseUrl, gameForm);
  }

  updateByEmptyStatistics(gameInfoModel: GameInfoModel) {
    return this.http.put<GameInfoModel>(this.baseUrl, gameInfoModel);
  }
}
