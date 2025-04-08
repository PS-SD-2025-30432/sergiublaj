import { ChefBase } from './chef-base.model';


export interface ChefRequest extends ChefBase {
  rating: number;
}
