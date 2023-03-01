import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

//TODO: REMOVE IMPLEMENTATION AFTER CONNECTION TO API!
export class AppComponent implements OnInit{
  title = 'poll-position-angular';

  //TODO: REMOVE EMAIL MOCK AFTER CONNECTION TO API!
  ngOnInit(): void {
    localStorage.setItem("email", "mcquilekarappen@gmail.com");
  }

}
