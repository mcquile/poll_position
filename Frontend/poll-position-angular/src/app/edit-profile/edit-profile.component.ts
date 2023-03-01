import { Component } from '@angular/core';
import {FormGroup, FormControl} from "@angular/forms";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent {
  profileForm = new FormGroup({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    branch: new FormControl(''),
    dateOfBirth: new FormControl(''),
    sex: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
  })

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn(this.profileForm.value);
  }
  updateProfile() {
    this.profileForm.patchValue({
      firstName: 'Nancy',
      lastName: 'Evils',
      branch: 'Johannesburg',
      dateOfBirth: '2000-01/01',
      sex: 'Female',
      email: 'nancy@email',
      password: 'nancy101'
    });
  }
}
