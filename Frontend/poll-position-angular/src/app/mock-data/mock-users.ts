import {User} from "../models/user";
import {BRANCHES} from "./mock-branches";
import {SEXES} from "./mock-sexes";

export const USERS: User[] = [
  {
    userID: "1",
    firstName: "McQuile",
    lastName: "Karappen",
    branch:BRANCHES[0],
    dateOfBirth: new Date(1999,3,9),
    profilePicLink: "https://media.istockphoto.com/id/1328887289/photo/happy-dog.jpg?b=1&s=170667a&w=0&k=20&c=mp3L73BC14QUuk1EQaYtZ1-wwJRW9HAffcsGZNyMy_o=",
    email: "mcquilekarappen@gmail.com",
    sex: SEXES[1],
    role: "Intern"
  },
  {
    userID: "2",
    firstName: "John",
    lastName: "Doe",
    branch:BRANCHES[2],
    dateOfBirth: new Date(1993,4,19),
    profilePicLink: "https://images.unsplash.com/photo-1518717758536-85ae29035b6d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8ZG9nc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=700&q=60",
    email: "johndoe@hotmail.com",
    sex: SEXES[1],
    role: "Executive"
  },
  {
    userID: "3",
    firstName: "Jane",
    lastName: "Doe",
    branch:BRANCHES[3],
    dateOfBirth: new Date(1994,5,18),
    profilePicLink: "https://images.unsplash.com/photo-1568572933382-74d440642117?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8ZG9nc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=700&q=60",
    email: "janedoe@outlook.com",
    sex: SEXES[0],
    role: "Architect"
  },
  {
    userID: "4",
    firstName: "Selena",
    lastName: "Kyle",
    branch:BRANCHES[5],
    dateOfBirth: new Date(1998,5,17),
    profilePicLink: "",
    email: "selenak@gmail.com",
    sex: SEXES[0],
    role: "Senior Developer"
  }
]
