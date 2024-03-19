import {  useEffect, useState } from 'react';
import { redirect } from '../utils/utils';

const Signin = () => {

	useEffect(() => {
		redirect()
	}, [])

	return (
		<section id="signin">
			<h2 className='mt-5'><center>Signing in ... </center></h2>
		</section>
	);
};

export default Signin;