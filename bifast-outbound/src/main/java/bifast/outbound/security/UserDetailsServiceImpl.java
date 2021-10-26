package bifast.outbound.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bifast.outbound.model.Channel;
import bifast.outbound.repository.ChannelRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ChannelRepository channelRepo;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Channel> oChannel = channelRepo.findById(userName);
		
		if (oChannel.isEmpty()) {
			throw new UsernameNotFoundException("Tidak ada channel dengan kode " + userName);
		}
		
		Users user = new Users();
		user.setChannel(oChannel.get());
		
		return user;
	}
	
	
}