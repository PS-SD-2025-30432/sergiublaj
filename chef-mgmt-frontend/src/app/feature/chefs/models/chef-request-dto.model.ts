import { ChefBaseDTO } from './chef-base-dto.model';


export interface ChefRequestDTO extends ChefBaseDTO {
  rating: number;
}
