import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {PollService} from "../../services/poll.service";
import {Router} from "@angular/router";
import {AppSettings} from "../../appSettings";

@Component({
  selector: 'app-create-poll',
  templateUrl: './create-poll.component.html',
  styleUrls: ['./create-poll.component.css']
})
export class CreatePollComponent implements OnInit {

  createPollForm = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    nominationEndTime: new FormControl(''),
    startTime: new FormControl(''),
    endTime: new FormControl(''),
  });
  nomineeForm = new FormGroup({
    nominees: new FormArray([]),
  });
  restrictionsForm = new FormGroup({
    restrictions: new FormArray([]),
  })

  constructor(private pollService: PollService, private router: Router) {
  }

  get restrictions(): FormArray {
    return this.restrictionsForm.get('restrictions') as FormArray;
  }

  get nominees(): FormArray {
    return this.nomineeForm.get('nominees') as FormArray;
  }

  createPoll(): void {
    let request = {
      title: this.createPollForm.value.title,
      description: this.createPollForm.value.description,
      voteStart: this.createPollForm.value.startTime,
      voteEnd: this.createPollForm.value.endTime,
      nominationEnd: this.createPollForm.value.nominationEndTime,
      nominations: this.nomineeForm.value.nominees,
      genericRestrictions: this.restrictionsForm.value.restrictions
    }
    console.log(request);
    this.pollService.createPoll(request).subscribe(poll => {
      console.log("hello")
      console.log(poll);
      this.router.navigateByUrl(`view-poll/${poll.id}`);
    });

  }

  removeRestriction() {
    this.restrictions.removeAt(this.restrictions.length - 1);
  }

  addRestriction() {
    this.restrictions.push(
      new FormGroup({
        firstName: new FormControl(''),
        lastName: new FormControl(''),
        sex: new FormControl(''),
        branch: new FormControl(''),
        dateOfBirthOlderThan: new FormControl(''),
        dateOfBirthYoungerThan: new FormControl('')
      })
    );
  }

  addNominee() {
    this.nominees.push(
      new FormGroup({
        nominee: new FormControl(''),
      })
    );
  }

  removeNominee() {
    this.nominees.removeAt(this.nominees.length - 1);
  }

  ngOnInit(): void {
    AppSettings.handleUserNotAuthenticated();
  }
}
