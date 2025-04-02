import { FilterRequestDTO } from '../../../shared/models/filter-request-dto.model';


export interface ChefFilterDTO extends FilterRequestDTO {
  name?: string;
  rating?: number;
  cnp?: number;
  birthDate?: string;
}
