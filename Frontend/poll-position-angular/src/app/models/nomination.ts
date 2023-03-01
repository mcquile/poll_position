import {Poll} from "./poll";
import {User} from "./user";

export interface Nomination{
  nominationID: number;
  nominee: string;
  votes: number[];
}
