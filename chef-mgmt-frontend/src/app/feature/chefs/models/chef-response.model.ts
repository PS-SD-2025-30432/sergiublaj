import { ChefBase } from './chef-base.model';


export interface ChefResponse extends ChefBase {
  id: string;
  numberOfStars: number;
}
