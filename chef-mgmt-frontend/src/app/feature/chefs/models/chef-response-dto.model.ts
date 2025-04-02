import { ChefBaseDTO } from './chef-base-dto.model';


export interface ChefResponseDTO extends ChefBaseDTO {
  id: string;
  numberOfStars: number;
}
