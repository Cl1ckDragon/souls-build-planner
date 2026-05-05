export interface Game {
  id: number;
  name: string;
  slug: string;
}

export interface GameClass {
  id: number;
  name: string;
  baseVigor: number;
  baseMind: number;
  baseEndurance: number;
  baseStrength: number;
  baseDexterity: number;
  baseIntelligence: number;
  baseFaith: number;
  baseArcane: number;
  baseLevel: number;
}

export interface Weapon {
  id: number;
  name: string;
  weaponType: string;
  weight: number;
}

export interface ArmourPiece {
  id: number;
  name: string;
  slot: 'head' | 'chest' | 'hands' | 'legs';
  weight: number;
}

export interface Build {
  id: string;
  title: string;
  slug: string;
  description: string;
  level: number;
  vigor: number;
  mind: number;
  endurance: number;
  strength: number;
  dexterity: number;
  intelligence: number;
  faith: number;
  arcane: number;
  isPublic: boolean;
  upvoteCount: number;
  game: Game;
  gameClass: GameClass;
  user: { username: string };
  createdAt: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  role: string;
}

export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
}
