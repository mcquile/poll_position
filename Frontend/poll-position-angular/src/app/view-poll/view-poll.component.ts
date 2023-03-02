import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Poll} from "../models/poll";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {Nomination} from "../models/nomination";

@Component({
  selector: 'app-view-poll',
  templateUrl: './view-poll.component.html',
  styleUrls: ['./view-poll.component.css']
})
export class ViewPollComponent implements OnInit{

  id: string;

  poll: Poll;

  nominations: Nomination[];

  constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router) {
    this.id=""
    this.nominations=[]
    this.poll = new class implements Poll {
      creator = new class implements User{
        email="";
        firstName="";
        lastName="";

      };
      description="";
      id= "";
      nominationEndTime= new Date();
      pollCreationTime= new Date();
      title= "string";
      voteEnd= new Date();
      voteStart= new Date();
    }
    this.route.params.subscribe( params => {
      this.id = params['id']
      this.http.get<Poll>(`http://localhost:8080/api/v1/polls/${this.id}`,{
        headers:{
          "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
        }
      }).subscribe(poll => this.poll=poll)
    });

  }

  getNominations(poll: Poll){
    this.http.get<Nomination[]>(`http://localhost8080/api/v1/polls/${poll.id}/nominations/`, {
      headers: {
        "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
      }
    }).subscribe(nominations => this.nominations=nominations)
  }

  ngOnInit(): void{

  }
}
