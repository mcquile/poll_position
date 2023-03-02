import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/user";
import {Poll} from "../models/poll";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUserByToken(): Observable<User>{
    return this.http.get<User>("http://localhost:8080/api/v1/users/me/", {
      headers: {
        "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
      }
    })
  }

}

