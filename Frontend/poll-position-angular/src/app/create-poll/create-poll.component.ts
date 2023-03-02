import { Component } from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {PollService} from "../poll.service";

@Component({
  selector: 'app-create-poll',
  templateUrl: './create-poll.component.html',
  styleUrls: ['./create-poll.component.css']
})
export class CreatePollComponent {

  constructor(private pollService: PollService) {
  }
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

  createPoll(): void {
    let request = {
      title: this.createPollForm.value.title,
      description: this.createPollForm.value.description,
      voteStartTime: this.createPollForm.value.startTime,
      voteEndTime: this.createPollForm.value.endTime,
      nominees: this.nomineeForm.value.nominees,
      genericRestrictions: this.restrictionsForm.value.restrictions
    }
    console.log(request)
    this.pollService.createPoll(request).subscribe(poll => console.log(poll));

  }

  removeRestriction(){
    this.restrictions.removeAt(this.restrictions.length-1);
  }
  addRestriction(){
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

  get restrictions(): FormArray {
    return this.restrictionsForm.get('restrictions') as FormArray;
  }

  get nominees(): FormArray {
    return this.nomineeForm.get('nominees') as FormArray;
  }

  addNominee() {
    this.nominees.push(
      new FormGroup({
        nominee: new FormControl(''),
      })
    );
  }

  removeNominee() {
    this.nominees.removeAt(this.nominees.length-1);
  }
}
