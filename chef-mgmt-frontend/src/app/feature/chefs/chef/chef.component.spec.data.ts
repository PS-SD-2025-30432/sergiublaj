import { UserResponse } from '../../profile/models/user-response.model';
import { Role } from '../../profile/models/user-role.enum';
import { ChefResponse } from '../models/chef-response.model';

export const mockChef: ChefResponse = {
  id: '1',
  name: '',
  numberOfStars: 5,
  cnp: '',
  birthDate: new Date().toISOString()
};

export const mockAdminUser: UserResponse = {
  id: '123',
  email: 'admin@example.com',
  name: 'Admin User',
  role: Role.ADMIN,
  cnp: '1234567890123',
  birthDate: '1990-01-01',
  rating: 5
};

export const mockModeratorUser: UserResponse = {
  id: '456',
  email: 'moderator@example.com',
  name: 'Moderator User',
  role: Role.MODERATOR,
  cnp: '9876543210987',
  birthDate: '1992-01-01',
  rating: 4
};
