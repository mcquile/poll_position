import {Branch} from "./branch";
import {Sex} from "./sex";

export interface User{
  userID: string;
  firstName: string;
  lastName: string;
  branch: Branch;
  dateOfBirth: Date;
  profilePicLink: string;
  email: string;
  sex: Sex;
  role: string;
}
