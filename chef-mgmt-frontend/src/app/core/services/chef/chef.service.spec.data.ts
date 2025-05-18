import { ChefRequest } from '../../../feature/chefs/models/chef-request.model';
import { ChefResponse } from '../../../feature/chefs/models/chef-response.model';
import { CollectionResponseDTO } from '../../../shared/models/collection-response-dto.model';

export const mockChefResponse: ChefResponse = {
  id: '123',
  name: 'Gordon Ramsay',
  cnp: '1234567890123',
  birthDate: new Date('1966-11-08').toISOString(),
  numberOfStars: 3
};

export const mockChefRequest: ChefRequest = {
  name: 'Gordon Ramsay',
  cnp: '1234567890123',
  birthDate: new Date('1966-11-08').toISOString(),
  rating: 4.5
};

export const mockChefCollectionResponse: CollectionResponseDTO<ChefResponse> = {
  pageNumber: 0,
  pageSize: 10,
  totalPages: 1,
  totalElements: 1,
  elements: [mockChefResponse]
};

